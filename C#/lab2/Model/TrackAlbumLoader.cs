using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Xml.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace lab2.Model {
    public static class TrackAlbumLoader {

        public static TrackAlbum CreateTrackAlbum()
        {
            Track[] tracks = {
                new Track("Bah", "Cmajor", "Classic", 1770),
                new Track("GreenDay", "Holiday", "Punk", 2005),
                new Track("Within Temptation", "Sinead", "Symphonic Metal", 2007),
                new Track("Metallica","The Black album","Heavy Metal",1991)
            };

            return new TrackAlbum(tracks);
        }

        public static TrackAlbum LoadFromTxt(string path) {
            List<Track> tracks = new List<Track>();

            if (!File.Exists(path)) {
                return null;
            }
            using (StreamReader reader = new StreamReader(path)) {
                string line = null;
                do {
                    line = reader.ReadLine();
                    if (line != null) {
                       string[] info = line.Split(' ');
                       if (info.Length == 4) {
                            tracks.Add(new Track(info[0], info[1],info[2],int.Parse(info[3])));
                       }
                    }
                } while (line != null);
            }

            return new TrackAlbum(tracks.ToArray<Track>());
        }
        public static TrackAlbum DeserializeFromBin(string path) {
        BinaryFormatter binFormatter = new BinaryFormatter();
            using (Stream stream = new FileStream(path, FileMode.Open)) { 
                TrackAlbum a = (TrackAlbum)binFormatter.Deserialize(stream);
                return a;
            }
        }
        public static TrackAlbum DeserializeFromAlbum(string path) {
            using (Stream stream = new FileStream(path, FileMode.Open)) {
                TrackAlbum a = (TrackAlbum)MySerializer.Deserialize(stream);
                return a;
            }
        }
    }
}