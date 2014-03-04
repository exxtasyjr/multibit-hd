package org.multibit.hd.ui.views.components;

import org.multibit.hd.ui.MultiBitUI;
import org.multibit.hd.ui.views.fonts.AwesomeDecorator;
import org.multibit.hd.ui.views.fonts.AwesomeIcon;
import org.multibit.hd.ui.views.themes.Themes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Utility to provide the following to UI:</p>
 * <ul>
 * <li>Provision of images</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class Images {

  /**
   * Utilities have no public constructor
   */
  private Images() {
  }


  /**
   * @return A new "qr code" image icon that's nicer than the Font Awesome version
   */
  public static Icon newQRCodeIcon() {

    try (InputStream is = Images.class.getResourceAsStream("/assets/images/qrcode.png")) {

      // Transform the mask color into the current themed text
      BufferedImage qrCodePng = ImageDecorator.applyColor(
        ImageIO.read(is),
        Themes.currentTheme.buttonText()
      );

      return new ImageIcon(qrCodePng);

    } catch (IOException e) {
      throw new IllegalStateException("The QR code image is missing");
    }

  }

  /**
   * @return A new "user" image icon suitable for use in tables
   */
  public static ImageIcon newUserIcon() {

    final Icon icon;

    icon = AwesomeDecorator.createIcon(
      AwesomeIcon.USER,
      Themes.currentTheme.text(),
      MultiBitUI.LARGE_ICON_SIZE
    );

    return ImageDecorator.toImageIcon(icon);

  }

  /**
   * @param confirmationCount The confirmation count
   * @param isCoinbase        True if this transaction requires the coinbase rules (120 confirmations)
   *
   * @return A new "confirmation" image icon suitable for use in tables
   */
  public static ImageIcon newConfirmationIcon(int confirmationCount, boolean isCoinbase) {

    BufferedImage background = new BufferedImage(MultiBitUI.NORMAL_ICON_SIZE, MultiBitUI.NORMAL_ICON_SIZE, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2 = background.createGraphics();

    g2.setRenderingHints(ImageDecorator.smoothRenderingHints());

    final int angle;
    if (isCoinbase) {
      angle = confirmationCount * 3 >= 360 ? 360 : confirmationCount * 3;
    } else {
      angle = confirmationCount * 60 >= 360 ? 360 : confirmationCount * 60;

    }
    g2.setColor(Color.GREEN);
    g2.fillArc(1, 1, MultiBitUI.NORMAL_ICON_SIZE - 2, MultiBitUI.NORMAL_ICON_SIZE - 2, 90, -angle);

    g2.setColor(Color.GREEN.darker());
    g2.drawArc(1, 1, MultiBitUI.NORMAL_ICON_SIZE - 2, MultiBitUI.NORMAL_ICON_SIZE - 2, 90, -angle);
    if (angle != 360) {
      int center = (int)( MultiBitUI.NORMAL_ICON_SIZE * 0.5);
      int diameter = center - 1;
      // vertical stroke
      g2.drawLine(center, center, center, 1);

      // angled stroke
      int xFinish = (int) (center + diameter * Math.cos(Math.toRadians(90 - angle)));
      int yFinish = (int) (center - diameter * Math.sin(Math.toRadians(90 - angle)));

      g2.drawLine(center, center, xFinish, yFinish);
    }

    g2.dispose();

    return ImageDecorator.toImageIcon(background);

  }

}