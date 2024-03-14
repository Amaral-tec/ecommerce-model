package br.com.amaral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import br.com.amaral.model.Product;

@Service
public class ProductService {

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public Product saveProductImages(Product product) throws IOException {

		for (int x = 0; x < product.getImages().size(); x++) {
			product.getImages().get(x).setProduct(product);
			product.getImages().get(x).setLegalEntity(product.getLegalEntity());

			String base64Image = "";

			if (product.getImages().get(x).getOriginalImage().contains("data:image")) {
				base64Image = product.getImages().get(x).getOriginalImage().split(",")[1];
			} else {
				base64Image = product.getImages().get(x).getOriginalImage();
			}

			byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

			if (bufferedImage != null) {

				int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				int width = Integer.parseInt("800");
				int height = Integer.parseInt("600");

				BufferedImage resizedImage = new BufferedImage(width, height, type);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(bufferedImage, 0, 0, width, height, null);
				g.dispose();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(resizedImage, "png", baos);

				product.getImages().get(x).setThumbnailImage(base64Image);

				bufferedImage.flush();
				resizedImage.flush();
				baos.flush();
				baos.close();
			}
		}
		return product;
	}
	
	public void sendLowStockAlert(Product product) throws MessagingException, UnsupportedEncodingException {
		if (Boolean.TRUE.equals(product.getIsAlerted()) && product.getQuantity() <= 10) {
			StringBuilder html = new StringBuilder();
			html.append("<h2>").append("Product: " + product.getName())
					.append(" with low stock: " + product.getQuantity());
			html.append("<p> Product ID:").append(product.getId()).append("</p>");

			String supplierEmail = product.getLegalEntity().getEmail();
			if (supplierEmail != null) {
				serviceSendEmail.sendEmailHtml("Product with low stock", html.toString(), supplierEmail);
			}
		}
	}
}
