using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Xml.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.IO.Compression;

namespace lab2.Model {
    class TrackAlbumSaver {
        public static void SaveToTxt(TrackAlbum trackAlbum, string path) {
            using (StreamWriter writer = new StreamWriter(path)) {
                foreach (var track in trackAlbum) {
                    writer.WriteLine(string.Format("{0} {1} {2} {3}",track.Compositor,track.Name,track.Genre,track.Release));
                }
            }
        }
        public static void SerializeToBin(TrackAlbum trackAlbum, string path) {
            BinaryFormatter binFormatter = new BinaryFormatter();
            using (Stream stream = new FileStream(path, FileMode.OpenOrCreate)) {
                binFormatter.Serialize(stream,trackAlbum);
            }
        }
        public static void SerializeToAlb(TrackAlbum trackAlbum, string path){
            
            using (Stream stream = new FileStream(path, FileMode.OpenOrCreate)) {
               MySerializer.Serialize(stream, trackAlbum);
            }
        }
    }
}
