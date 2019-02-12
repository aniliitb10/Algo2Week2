import edu.princeton.cs.algs4.Picture;

class Helper
{
  static <T> T requireNotNull(T object)
  {
    if (object == null)
    {
      throw new IllegalArgumentException("argument is null!");
    }

    return object;
  }

  private static int getRed(int rgb)   { return rgb >> 16 & 255; }
  private static int getGreen(int rgb) { return rgb >> 8 & 255; }
  private static int getBlue(int rgb)  { return rgb & 255; }

  static double energy(Picture picture, int colIndex, int rowIndex)
  {
    if (rowIndex == picture.height()) // special top/bottom or left-most/right-most points
    {
      return 0.0;
    }

    if (colIndex == 0 || rowIndex == 0 || colIndex == (picture.width() - 1) || rowIndex == (picture.height() -1 ))
    {
      return 1000.0;
    }

    int leftRgb = picture.getRGB(colIndex-1, rowIndex);
    int rightRgb = picture.getRGB(colIndex+1, rowIndex);

    int rx = Helper.getRed(leftRgb) + Helper.getRed(rightRgb);
    int gx = Helper.getGreen(leftRgb) + Helper.getGreen(rightRgb);
    int bx = Helper.getBlue(leftRgb) + Helper.getBlue(rightRgb);
    int xSquare = rx*rx + gx*gx + bx*bx;

    int topRgb = picture.getRGB(colIndex, rowIndex-1);
    int bottomRgb = picture.getRGB(colIndex, rowIndex+1);

    int ry = Helper.getRed(topRgb) + Helper.getRed(bottomRgb);
    int gy = Helper.getGreen(topRgb) + Helper.getGreen(bottomRgb);
    int by = Helper.getBlue(topRgb) + Helper.getBlue(bottomRgb);
    int ySquare = ry*ry + gy*gy + by*by;

    return Math.sqrt(xSquare + ySquare);
  }

}
