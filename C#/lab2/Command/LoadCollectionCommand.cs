using lab2.ViewModel;
using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using lab2.Model;
using lab2.ViewModel;

namespace lab2.Command {
    class LoadCollectionCommand : ICommand{
        private TrackAlbum Album;
        public LoadCollectionCommand(TrackAlbum Album) {
            this.Album = Album;
        }
        public bool CanExecute(object parameter) {
            return true;
        }

        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter) {
           
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "Текстовые файлы|*.txt|Бинарные файлы|*.dat|Десериализация|*.alb";
            openFileDialog.InitialDirectory = @"D:\2cours\ИСП\";
            openFileDialog.ShowDialog();
            string path = openFileDialog.FileName;
            TrackAlbum a = null;
        
            if (path.EndsWith("txt")){
                a = Model.TrackAlbumLoader.LoadFromTxt(path);
            } else if (path.EndsWith("dat")) {
                a = Model.TrackAlbumLoader.DeserializeFromBin(path);
            }
            else if (path.EndsWith("alb")) {
                a = Model.TrackAlbumLoader.DeserializeFromAlbum(path);
            }
            
            if (a != null) {
                foreach (Track track in a) {
                    (parameter as ViewModel.MainViewModel).Album.Add(track);
                }
            }
        }
    }
}
