final class SimplePoint
{
  final int colIndex;
  final int rowIndex;

  SimplePoint(int colIndex, int rowIndex)
  {
    this.colIndex = colIndex;
    this.rowIndex = rowIndex;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (obj == this) return true;

    if (obj.getClass() != this.getClass()) return false;
    SimplePoint other = (SimplePoint) obj;

    return (this.colIndex == other.colIndex) && (this.rowIndex == other.rowIndex);
  }
}