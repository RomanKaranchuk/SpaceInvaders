using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;

namespace lab2.Model {
    [Serializable]
    public class Track {
        public Track(/*int tID,*/ string tCompositor,
                     string tName, string tGenre,
                     int tRelease) {
            //this.TrackID = tID;
            this.Compositor = tCompositor;
            this.Name = tName;
            this.Genre = tGenre;
            this.Release = tRelease;
        }
        /*public int TrackID {
            get { return _trackID; }
            set { _trackID = value < 0 ? 0 : value; }
        }*/
        public string Compositor {
            get;
            set;
        }
        public string Name {
            get;
            set;
        }
        public string Genre {
            get;
            set;
        }
        public int Release {
            get;
            set;
        }
        //private int _trackID;
        //private string _compositor;
        //private string _name;
        //private string _genre;
        //private int _release;
    }
}
