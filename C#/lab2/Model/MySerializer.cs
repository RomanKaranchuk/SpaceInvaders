using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Runtime.Serialization;
using System.Reflection;

namespace lab2.Model {
    public static class MySerializer {
        public static void Serialize(Stream stream, object graph) {
            using (BinaryWriter binWriter = new BinaryWriter(stream)) {
                Type type = graph.GetType();
                binWriter.Write(type.AssemblyQualifiedName);
                Write(type, graph, binWriter);
            }
        }
        private static void WritePrimitive(dynamic obj, BinaryWriter binWriter) {
            binWriter.Write(obj);
        }
        private static void Write(Type type, object obj, BinaryWriter binWriter) {
            if (type.IsPrimitive && type != typeof(string)) {
                WritePrimitive(obj,binWriter);
            }
            else {
                binWriter.Write(obj == null);
                if (obj != null) {
                    Type basetype = type.BaseType;
                    if (basetype == typeof(Array)) {
                        binWriter.Write((obj as Array).Length);
                        foreach (object item in (obj as Array)) {
                            if(item != null)
                            Write(item.GetType(),item,binWriter);
                            else
                            Write(typeof(Nullable),null, binWriter);
                        }
                        return ;
                    }
                    if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>)) {
                        Write(type.GetGenericArguments()[0],obj,binWriter);
                        return;
                    }
                    if (type == typeof(string)) {
                        binWriter.Write(obj as string);
                        return ;
                    }
                    if (basetype != typeof(object)) Write(basetype,obj,binWriter);
                    FieldInfo[] fields =type.GetFields(BindingFlags.Instance | BindingFlags.Public |
                        BindingFlags.NonPublic | BindingFlags.DeclaredOnly);
                    foreach (FieldInfo fieldinfo in fields) {
                        if (!fieldinfo.GetCustomAttributes<NonSerializedAttribute>().Any())
                            Write(fieldinfo.FieldType, fieldinfo.GetValue(obj),binWriter);
                    }
                }
            }
        }

        public static object Deserialize(Stream stream) {
            using (BinaryReader binReader = new BinaryReader(stream)) {
                Type type = Type.GetType(binReader.ReadString());
                object obj = null;
                Read(binReader, type, ref obj);
                return obj;
            }
        }
        private static object ReadPrimitive(BinaryReader binReader, Type type) {
            if (type == typeof(int)){
                return binReader.ReadInt32(); 
            } else if (type == typeof (bool)) {
                return binReader.ReadBoolean();
            } else if (type == typeof(string)) {
                return binReader.ReadString();
            } else if (type == typeof(char)) {
                return binReader.ReadChar();
            } else if (type == typeof(byte)) {
                return binReader.ReadByte();
            } else if (type == typeof(double)){
                return binReader.ReadDouble();
            }
            throw new SerializationException("Can't deserialize type "+ type.Name);
        }
        private static void Read(BinaryReader br, Type type, ref object obj) {
            if (type.IsPrimitive && type != typeof(string)) {
                obj = ReadPrimitive(br,type);
                return ;
            } else {
                if (br.ReadBoolean()) return;
                if (type == typeof(string)) {
                    obj = br.ReadString();
                    return ;
                }
            }
            if (type.BaseType == typeof(Array)) {
                int count = br.ReadInt32();
                Type ellementtype = type.GetElementType();
                Array newobject = Array.CreateInstance(ellementtype,count);
                for (int i = 0; i < count; i++) {
                    object temp = null;
                    Read(br, ellementtype, ref temp);
                    newobject.SetValue(temp,i);
                }
                obj = newobject;
                return;
            }
            if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>)) {
                Read(br, type.GetGenericArguments()[0],ref obj);
                return ;
            }
            if (obj == null) {
                obj = FormatterServices.GetUninitializedObject(type);
            }
            if (type.BaseType != typeof(object)) Read(br, type.BaseType, ref obj);
            foreach (FieldInfo fieldinfo in type.GetFields(BindingFlags.Instance |
                BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.DeclaredOnly)) {
                    if (!fieldinfo.GetCustomAttributes<NonSerializedAttribute>().Any()) {
                        object value = null;
                        Read(br, fieldinfo.FieldType, ref value);
                        fieldinfo.SetValue(obj, value);
                    }
            }
        }

    }
}
