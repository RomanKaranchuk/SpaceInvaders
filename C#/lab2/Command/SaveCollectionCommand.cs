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
    class SaveCollectionCommand : ICommand{
        private TrackAlbum Album;
        public bool CanExecute(object parameter) {
            return true;
        }

        public event EventHandler CanExecuteChanged;
        public SaveCollectionCommand() {

        }
        public SaveCollectionCommand(TrackAlbum Album) {
            this.Album = Album;
        }
        public void Execute(object parameter) {
           SaveFileDialog SaveFileDialog = new SaveFileDialog();
           SaveFileDialog.Filter = "Текстовые файлы|*.txt|Бинарные файлы|*.dat|Cериализация|*.alb";
           SaveFileDialog.InitialDirectory = @"D:\2cours\ИСП\";
           SaveFileDialog.ShowDialog();
           string path = SaveFileDialog.FileName;
           if (path.EndsWith("txt")) {
               Model.TrackAlbumSaver.SaveToTxt(Album, path);
           } else if (path.EndsWith("dat")){
               Model.TrackAlbumSaver.SerializeToBin(Album,path);
           } else if (path.EndsWith("alb")) {
               Model.TrackAlbumSaver.SerializeToAlb(Album,path);
           }
        }
    }
}
