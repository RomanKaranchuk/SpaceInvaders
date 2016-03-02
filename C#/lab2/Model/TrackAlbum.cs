using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Collections.Specialized;


namespace lab2.Model {
    [Serializable]
    public class TrackAlbum : ICollection, IEnumerable<Track>, INotifyCollectionChanged {
        private Track[] _album;
        //public int size=1;
        public TrackAlbum() {
            _album = new Track[0];
        }
        public TrackAlbum(Track[] tArray) {
            _album = new Track[tArray.Length];

            for (int i = 0; i < tArray.Length; i++) {
                _album[i] = tArray[i];
                size++;
            }
        }
        public TrackAlbum(IEnumerable<Track> collTracks) {
            if (collTracks != null) {
                _album = new Track[1];
                foreach (var track in collTracks) {
                    this.Add(track as Track);
                }
            }
        }
        IEnumerator IEnumerable.GetEnumerator() {
            return GetEnumerator();
        }
        
        public IEnumerator<Track> GetEnumerator() {
            for (int i = 0; i < size; i++) {
                yield return _album[i];
            }
        }
        
        private int new_length=1,size=0;
        
        public void Add(Track _track) {
            
            if ( size == _album.Length) {             
                 new_length = _album.Length*2+1;               
            }
            
            Track[] _album_new = new Track[new_length];
            _album.CopyTo(_album_new, 0);
            _album = _album_new;
            _album[size] =_track;
            size++;
            
            RaiseCollectionChanged(NotifyCollectionChangedAction.Add, _track);           
        }
        public void Remove(Track _track) {
            if (size != 0) {
                Track[] _albom_new = new Track[_album.Length-1];
                for (int i = 0,j = 0; i < size; i++) {
                    if (_track != _album[i])
                    {
                        _albom_new[j] = _album[i];
                        j++;
                    }
                }
                _album = _albom_new;
                size--;
                RaiseCollectionChanged(NotifyCollectionChangedAction.Reset, null);
            }
        }
        public int Find(Track _track) {
            //for (int i = 0; i < _album.Length; i++) {
            //    if (_album[i].Equals( _track))
            //        return i;
            //}
            return -1;
        }
        public void Show() {
            //foreach (Track t in _album) {
            //    Console.WriteLine("{0,-4}{1,-20}{2,-15}{3,-15}{4,0}"
            //                      , t.Compositor
            //                       , t.Name,
            //                      t.Genre,
            //                      t.Release);
            //}
        }


        public void CopyTo(Array array, int index) {
        }

        public int Count {
            get {
                return _album.Length;
            }
        }

        public bool IsSynchronized {
            get { return false; }
        }

        public object SyncRoot {
            get { return null; }
        }
        [field: NonSerialized()]
        public event NotifyCollectionChangedEventHandler CollectionChanged;

        private void RaiseCollectionChanged(NotifyCollectionChangedAction action, object obj) {
            var handler = CollectionChanged;
            if (handler != null) {
                handler(this, new NotifyCollectionChangedEventArgs(action, obj));
            }
        }

        void ICollection.CopyTo(Array array, int index) {
            throw new NotImplementedException();
        }

        int ICollection.Count {
            get { throw new NotImplementedException(); }
        }

        bool ICollection.IsSynchronized {
            get { throw new NotImplementedException(); }
        }

        object ICollection.SyncRoot {
            get { throw new NotImplementedException(); }
        }
    }
}
