using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using lab2.ViewModel;
using lab2.Model;

namespace lab2.Command {
    class AddCommand : ICommand {
        
        private TrackAlbum trackAlbum;
        public AddCommand(TrackAlbum trackAlbum) {
            this.trackAlbum = trackAlbum;
        }
        public bool CanExecute(object parameter) {
            return true;
        }


        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter) {
            var viewModel = parameter as MainViewModel; 
            viewModel.Album.Add(viewModel.SelectedTrack);
            viewModel.SelectedTrack =new Track("","","",0);
        }
    }
}
