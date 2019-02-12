import edu.princeton.cs.algs4.Picture;

public class SeamCarver
{
  private Picture picture;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture)
  {
    this.picture = new Picture(Helper.requireNotNull(picture));
  }

  // current picture
  public Picture picture()
  {
    return new Picture(picture);
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

  // energy of pixel at column colIndex and row rowIndex
  public double energy(int colIndex, int rowIndex)
  {
    validatePoint(colIndex, rowIndex);
    return Helper.energy(picture, null, colIndex, rowIndex);
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam()
  {
    PseudoSP sp = new PseudoSP(picture, false);
    return sp.seamPoints(false);
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam()
  {
    PseudoSP sp = new PseudoSP(picture, true);
    return sp.seamPoints(true);
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam)
  {
    if (picture.height() <= 1) throw new IllegalArgumentException("Height of picture is " + picture.height());
    validateSeam(seam, false);

    Picture newPicture = new Picture(picture.width(), picture.height() - 1);

    for (int colIndex = 0; colIndex < picture.width(); ++colIndex)
    {
      int picRowCounter = 0;

      for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
      {
        if (seam[colIndex] == rowIndex) continue;

        newPicture.setRGB(colIndex, picRowCounter++, picture.getRGB(colIndex, rowIndex));
      }
    }

    picture = newPicture;
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam)
  {
    if (picture.width() <= 1) throw new IllegalArgumentException("Width of picture is " + picture.width());
    validateSeam(seam, true);

    Picture newPicture = new Picture(picture.width() -1, picture.height());

    for (int rowIndex = 0; rowIndex < picture.height(); ++rowIndex)
    {
      int picColCounter = 0;

      for (int colIndex = 0; colIndex < picture.width(); ++colIndex)
      {
        if (seam[rowIndex] == colIndex) continue;

        newPicture.setRGB(picColCounter++, rowIndex, picture.getRGB(colIndex, rowIndex));
      }
    }

    picture = newPicture;
  }

  private void validatePoint(int colIndex, int rowIndex)
  {
    validateColumnIndex(colIndex);
    validateRowIndex(rowIndex);
  }

  private void validateRowIndex(int rowIndex)
  {
    if (rowIndex < 0 || rowIndex >= picture.height())
    {
      throw new IllegalArgumentException("Invalid row index: " + rowIndex + ", valid range: [0," + picture.height() + ")");
    }
  }

  private void validateColumnIndex(int colIndex)
  {
    if (colIndex < 0 || colIndex >= picture.width())
    {
      throw new IllegalArgumentException("Invalid column index: " + colIndex + ", valid range: [0," + picture.width() + ")");
    }
  }

  private void validateSeam(int[] seam, boolean verticalSeam)
  {
    Helper.requireNotNull(seam);

    if (verticalSeam && seam.length != picture.height()) throw new IllegalArgumentException("Invalid length: " + seam.length);
    if (!verticalSeam && seam.length != picture.width()) throw new IllegalArgumentException("Invalid length: " + seam.length);

    int lastElem = seam[0];

    if (verticalSeam) validateColumnIndex(lastElem);
    else              validateRowIndex(lastElem);

    for (int index = 1; index < seam.length; ++index)
    {
      int latestElem = seam[index];

      if (verticalSeam) validateColumnIndex(latestElem);
      else              validateRowIndex(latestElem);

      if (Math.abs(latestElem - lastElem) > 1)
      {
        throw new IllegalArgumentException("The difference of adjacent entries should not exceed 1: seam[" + index
         + "] - seam[" + (index - 1) + "] = " + Math.abs(latestElem - lastElem));
      }

      lastElem = latestElem;
    }
  }
}
