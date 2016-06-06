using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace lab2.Command {
    class langCommand : ICommand {
        public bool CanExecute(object parameter) {
            return true;
        }

        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter) {
            var temp = parameter as string;
            System.Threading.Thread.CurrentThread.CurrentUICulture = System.Globalization.CultureInfo.CreateSpecificCulture(temp);
            lab2.Resources.Strings.OnChanged();
            lab2.Properties.Settings.Default["Language"] = temp;
            lab2.Properties.Settings.Default.Save();
        }
    }
}
