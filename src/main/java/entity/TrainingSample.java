package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import servise.ImageService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSample {
		String url;
		double[] vectorOfInputValues;
		double[] vectorOfOutputValues;
		private final static int OOOOOMAYYYYYY = 0;
		private final static int OOOOOMAYYYYYY2 = 0;

		public void init(String urlImage, int countOutputValues, int numberOutputValues, int size) throws IOException {
				url = urlImage;
				BufferedImage coloredImage = ImageIO.read(new File(url));
				coloredImage = ImageService.resize(coloredImage, size,size);
				coloredImage = ImageService.blackNWhiteFilter(coloredImage);
				vectorOfInputValues = ImageService.getVectorOfInputValues(coloredImage);
				vectorOfOutputValues = new double[countOutputValues];
				Arrays.fill(vectorOfOutputValues, 0);
				vectorOfOutputValues[numberOutputValues - 1] = 1;
		}
}


