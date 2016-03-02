using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;// for INotifyPropertyChanged

namespace lab2.ViewModel {
    public class ViewModelBase : INotifyPropertyChanged {
        protected ViewModelBase() 
        { 
        }
        public virtual string DisplayName { get; protected set; }
        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged(string propertyName) {
            PropertyChangedEventHandler handler = this.PropertyChanged;
            if (handler != null) {
                var e = new PropertyChangedEventArgs(propertyName);
                handler(this, e);
            }
        }
        public void Dispose() {
            this.OnDispose();
        }
        protected virtual void OnDispose() { }
    }
}
