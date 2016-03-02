using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using lab2.ViewModel;
using lab2.Model;

namespace lab2.Command {
    class RemoveCommand:ICommand {

        public bool CanExecute(object parameter) {
            return true;
        }

        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter) {
           var viewModel = parameter as MainViewModel;
           viewModel.Album.Remove(viewModel.SelectedTrack);
           viewModel.SelectedTrack = new Track("", "", "", 0);
        }
    }
}
