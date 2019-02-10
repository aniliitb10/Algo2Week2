import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class PseudoDFS
{
  private final boolean[][] visited;
  private final Stack<Integer> reversePostOrder;

  public PseudoDFS(Picture picture, boolean verticalSeam)
  {
    //TODO: Is the dimension correct?
    visited = new boolean[picture.height()][picture.width()];
    reversePostOrder = new Stack<>();

    for (int rowIndex = 0; rowIndex < picture.width(); ++rowIndex)
    {
      for (int colIndex = 0; colIndex < picture.height(); ++colIndex)
      {
        if (!visited[rowIndex][colIndex])
        {
          if (verticalSeam)
          {
            verticalDfs(picture, rowIndex, colIndex);
          }
          else
          {
            horizontalDfs(picture, rowIndex, colIndex);
          }
        }
      }
    }
  }

  private void verticalDfs(Picture picture, int rowIndex, int colIndex)
  {
    visited[rowIndex][colIndex] = true;

    // below
    if ((colIndex < (picture.height() - 1)) && !visited[rowIndex][colIndex + 1])
    {
      verticalDfs(picture, rowIndex, colIndex + 1);
    }

    // left-below
    if ((rowIndex > 0) && (colIndex < (picture.height() - 1)) && !visited[rowIndex - 1][colIndex + 1])
    {
      verticalDfs(picture, rowIndex - 1, colIndex + 1);
    }
    
    // right-below
    if ((rowIndex < (picture.width() - 1)) && (colIndex < (picture.height() - 1)) && !visited[rowIndex + 1][colIndex + 1])
    {
      verticalDfs(picture, rowIndex + 1, colIndex + 1);
    }

    reversePostOrder.push(rowIndex);
  }

  private void horizontalDfs(Picture picture, int rowIndex, int colIndex)
  {
    visited[rowIndex][colIndex] = true;

    // right
    if ((rowIndex < (picture.width() - 1)) && !visited[rowIndex + 1][colIndex])
    {
      horizontalDfs(picture, rowIndex + 1, colIndex);
    }

    // right-below
    if ((rowIndex < (picture.width() - 1)) && (colIndex < (picture.height() - 1)) && !visited[rowIndex + 1][colIndex + 1])
    {
      horizontalDfs(picture, rowIndex + 1, colIndex + 1);
    }

    // right-up
    if ((rowIndex < (picture.width() - 1)) && (colIndex > 0) && !visited[rowIndex + 1][colIndex - 1])
    {
      horizontalDfs(picture, rowIndex + 1, colIndex - 1);
    }

    reversePostOrder.push(colIndex);
  }

  public Iterable<Integer> order()
  {
    return reversePostOrder;
  }
}
