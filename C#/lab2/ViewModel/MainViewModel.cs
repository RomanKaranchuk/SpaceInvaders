using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using lab2.Model;
using System.Collections.ObjectModel; // for ObservableCollection<T>
using lab2.Command;
using System.Windows.Input;
using System.ComponentModel;

namespace lab2.ViewModel {
    public class MainViewModel : ViewModelBase, INotifyPropertyChanged{
       
        private Track _selectedTrack;
        public Track SelectedTrack {
            get {
                return _selectedTrack;
            }
            set {
                _selectedTrack = value;
                OnPropertyChanged("SelectedTrack");
            }
        }
        public MainViewModel(TrackAlbum Album) {
        }
        public MainViewModel() {
            QueryRelease=null;
            Album = new TrackAlbum();
            AddCommand = new AddCommand(Album);
            RemoveCommand = new RemoveCommand();
            OpenFileCommand = new LoadCollectionCommand(Album);
            SaveFileCommand = new SaveCollectionCommand(Album);
            ChangeLangCommand = new langCommand();
            _selectedTrack = new Track("", "", "", 0);
        }
        public ICommand AddCommand { get; set; }
        public ICommand ChangeLangCommand { get; set;}
        public ICommand RemoveCommand { get; set; }
        public ICommand OpenFileCommand { get; set;}
        public ICommand SaveFileCommand { get; set;}

        public ICommand searchCommand { get;set;}

        private ReleaseComparer searchComparer;

        private int? queryRelease;
        private TrackAlbum trackAlbum;
        public TrackAlbum Album {
            get {
                TrackAlbum requestedTrackAlbum = trackAlbum;
                if (QueryRelease != null) {
                    IEnumerable<Track> request;
                    switch (SearchComparer) {
                        case ReleaseComparer.Greater:
                            request = from track in requestedTrackAlbum
                                      where track.Release > QueryRelease
                                      select track;
                            break;
                        case ReleaseComparer.Lower:
                            request = from track in requestedTrackAlbum
                                      where track.Release < QueryRelease
                                      select track;
                            break;
                        case ReleaseComparer.Equal:
                            request = from track in requestedTrackAlbum
                                      where track.Release == QueryRelease
                                      select track;
                            break;
                        default:
                            request = trackAlbum;
                            break;
                    }
                    requestedTrackAlbum = new TrackAlbum(request);
                }
                return requestedTrackAlbum;
            }
            set {
                this.trackAlbum = value;
            }
        }
        
        public int? QueryRelease {
            get {
                return queryRelease;
            }
            set {
                queryRelease=value;
                RaisePropertyChanged("Album");
            }
        }
        public event PropertyChangedEventHandler PropertyChanged;
        public ReleaseComparer SearchComparer{
            get {
                return searchComparer;
            }
            set {
                searchComparer = value;
                RaisePropertyChanged("Album");
            }
        }
        private void RaisePropertyChanged(string propertyName) {
            if (PropertyChanged != null) {
                PropertyChanged(this,new PropertyChangedEventArgs(propertyName));
            }
        }
    }
}
