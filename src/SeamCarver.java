import edu.princeton.cs.algs4.Picture;

public class SeamCarver
{
  private final Picture picture;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture)
  {
    this.picture = Helper.requireNotNull(picture);
  }

  // current picture
  public Picture picture()
  {
    return picture;
  }

  // width of current picture
  public int width()
  {
    return picture.width();
  }

  // height of current picture
  public int height()
  {
    return picture.height();
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y)
  {
    validatePoint(x,y);

    if (x == 0 || y == 0 || x == (width() - 1) || y == (height() -1 ))
    {
      return 1000.0;
    }

    int leftRgb = picture.getRGB(x-1, y);
    int rightRgb = picture.getRGB(x+1, y);

    int rx = Helper.getRed(leftRgb) + Helper.getRed(rightRgb);
    int gx = Helper.getGreen(leftRgb) + Helper.getGreen(rightRgb);
    int bx = Helper.getBlue(leftRgb) + Helper.getBlue(rightRgb);
    int xSquare = rx*rx + gx*gx + bx*bx;

    int topRgb = picture.getRGB(x, y-1);
    int bottomRgb = picture.getRGB(x, y+1);

    int ry = Helper.getRed(topRgb) + Helper.getRed(bottomRgb);
    int gy = Helper.getGreen(topRgb) + Helper.getGreen(bottomRgb);
    int by = Helper.getBlue(topRgb) + Helper.getBlue(bottomRgb);
    int ySquare = ry*ry + gy*gy + by*by;

    return Math.sqrt(xSquare + ySquare);
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam()
  {
    return null;
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam()
  {
    return null;
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam)
  {
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam)
  {
  }

  public void validatePoint(int x, int y)
  {
    if (x < 0 || y < 0) throw new IllegalArgumentException();
    if (x >= width() || y >= height()) throw new IllegalArgumentException();
  }
}
