using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2.Model {
    public class TrackAlbumEnum : IEnumerator<Track> {
        public Track[] _album;

        int position = -1;

        public TrackAlbumEnum(Track[] list) {
            _album = list;
        }

        public bool MoveNext() {
            position++;
            return (position < _album.Length);
        }

        public void Reset() {
            position = -1;
        }

        object IEnumerator.Current {
            get { return Current; }
        }

        public Track Current {
            get {
                try {
                    return _album[position];
                }
                catch (IndexOutOfRangeException) {
                    throw new InvalidOperationException();
                }
            }
        }
        public void Dispose() { }
    }
}
