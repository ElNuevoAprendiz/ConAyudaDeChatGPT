import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*Por favor verificar que se modifico el classpath, 
Para poder configurar el classpath y librerías primero tenemos que 
instalar la extensión Extension Pack for Java, después abrir la configuración 
del classpath y liberías haciendo lo siguiente:


   - F1
   - "Java configure classpath"

En la configuración vemos que el classpath está indicado como la carpeta raíz 
del proyecto (.) en este caso el classpath debería de ser src y en el listado 
de librerías vemos que no hay ninguna, deberíamos de añadir la libería de
sqlite que es el jar que descargamos y pusimos en la carpeta lib
*/

    public class Sample
    {
      public static void main(String[] args)
      {
        // NOTE: Connection and Statement are AutoCloseable.
        //       Don't forget to close them both in order to avoid leaks.
        try
        (
          // create a database connection
          Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
          Statement statement = connection.createStatement();
        )
        {
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists person");
          statement.executeUpdate("create table person (id integer, name string)");
          statement.executeUpdate("insert into person values(1, 'leo')");
          statement.executeUpdate("insert into person values(2, 'yui')");
          ResultSet rs = statement.executeQuery("select * from person");
          while(rs.next())
          {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
          }
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          e.printStackTrace(System.err);
        }
      }
    }