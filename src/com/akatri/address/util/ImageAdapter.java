package com.akatri.address.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageAdapter extends XmlAdapter<String, Image> {

	@Override
	public Image unmarshal(String data) throws Exception {

		return (data == null || data.isEmpty()) ? null
				: new Image(new ByteArrayInputStream(Base64.getDecoder().decode(data)));
	}

	@Override

	public String marshal(Image image) throws Exception {
		if (image == null) {
			return " ";
		}
		BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bimg, "jpg", os);
		//os.close();
		return Base64.getEncoder().encodeToString(os.toByteArray());
		
	}

}
