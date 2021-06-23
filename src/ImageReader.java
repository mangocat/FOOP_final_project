import java.util.List;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;

public class ImageReader {
    public static List<Image> read(String imageFolderPath, double scale){
        // https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html#create
        // Path dir = FileSystems.getDefault().getPath(imageFolderPath);
        Path dir = Paths.get(imageFolderPath);
        List<File> files = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        // https://docs.oracle.com/javase/tutorial/essential/io/dirs.html#listdir
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
            for(Path filePath: stream){
                files.add(filePath.toFile());
            }
            // sort
            sortFileByName(files);
            // convert to images
            for(File file: files){
                try{
                    Image original = ImageIO.read(file);
                    Image newImage = original.getScaledInstance((int)((double)original.getWidth(null) * scale), (int)((double)original.getHeight(null) * scale), Image.SCALE_DEFAULT);
                    images.add(newImage);
                }catch(IOException e){
                    throw new RuntimeException();
                }
            }
        }catch(IOException e){
            throw new IllegalArgumentException(e);
        }
        return images;
    }
    public static void sortFileByName(List<File> files){
        files.sort((file1, file2) -> {
            int index1 = Integer.parseInt(file1.getName().split("\\.")[0]);
            int index2 = Integer.parseInt(file2.getName().split("\\.")[0]);
            return index1 - index2;
        });
    }
}
