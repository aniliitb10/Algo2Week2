import edu.princeton.cs.algs4.Picture;

public class Client
{
  public static void main(String[] args)
  {
    Picture originalPicture = new Picture("./src/original.png");
    System.out.println("Original: " + originalPicture.width() + " x " + originalPicture.height());

    Picture shrunkPicture = new Picture("./src/shrunk.png");
    System.out.println("Shrunk: " + shrunkPicture.width() + " x " + shrunkPicture.height());

    SeamCarver seamCarver = new SeamCarver(originalPicture);

    for (int index = 0; index < 150; ++index)
    {
      seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
    }

    System.out.println("After modification: " + seamCarver.picture().width() + " x " + seamCarver.picture().height());
    if (seamCarver.picture().equals(shrunkPicture))
    {
      System.out.println("Working!");
    }
    else
    {
      System.out.println("NOT Working!"); // it doesn't match pixel by pixel, but that shouldn't be an issue!
    }

    seamCarver.picture().save("./src/modified.png");
  }
}
