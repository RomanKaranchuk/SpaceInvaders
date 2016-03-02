using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using lab2.Model;
using lab2.Command;
using System.Windows.Input;
using System.ComponentModel;


namespace lab2.ViewModel {
    public class TrackViewModel:ViewModelBase {
        public Track Track;
        public TrackViewModel(Track track) {
            this.Track = track;
        }
        public string Compositor {
            get { return Track.Compositor; }
            set { 
                Track.Compositor = value;
                OnPropertyChanged("Compositor");
            }
        }
        public string Name {
            get { return Track.Name; }
            set {
                Track.Name = value; 
                OnPropertyChanged("Name");
            }
        }
        public string Genre {
            get { return Track.Genre; }
            set { 
                Track.Genre = value; 
                OnPropertyChanged("Genre");
            }
        }
        public int Release {
            get { return Track.Release; }
            set { 
                Track.Release = value < 0 ? 0 : value; 
                OnPropertyChanged("Release");
            }
        }
    }
}
