public class Helper
{
  public static <T> T requireNotNull(T object)
  {
    if (object == null)
    {
      throw new IllegalArgumentException("argument is null!");
    }

    return object;
  }

  public static int getRed(int rgb)   { return rgb >> 16 & 255; }
  public static int getGreen(int rgb) { return rgb >> 8 & 255; }
  public static int getBlue(int rgb)  { return rgb & 255; }
}
