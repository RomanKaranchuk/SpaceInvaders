using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab2.Resources {
    public partial class Strings {
        public static event PropertyChangedEventHandler StaticPropertyChanged;
        public static void OnChanged() {
            if (StaticPropertyChanged != null)
                StaticPropertyChanged(null, new PropertyChangedEventArgs(""));
        }
    }
}
