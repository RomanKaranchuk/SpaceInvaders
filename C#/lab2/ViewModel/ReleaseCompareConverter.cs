using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;


namespace lab2.ViewModel {
    public class ReleaseCompareConverter:IValueConverter {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture) {
            ReleaseComparer sign = (ReleaseComparer)value;
            switch (sign) {
                case ReleaseComparer.Greater:
                    return ">";
                case ReleaseComparer.Lower:
                    return "<";
                case ReleaseComparer.Equal:
                    return "=";
                default:
                    return ">";       
            }
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture) {
            string sign = (string)value;
            switch (sign) {
                case ">":
                    return ReleaseComparer.Greater;
                case "<":
                    return ReleaseComparer.Lower;
                case "=":
                    return ReleaseComparer.Equal;
                default:
                    return ReleaseComparer.Greater;
            }
        }
    }
}
