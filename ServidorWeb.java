import java.io.* ;
import java.net.* ;

public final class ServidorWeb
{
        public static void main(String argv[]) throws Exception
        {
        int puerto = 6789;

        // Estableciendo el socket de escucha.
        ServerSocket socketdeEscucha = new ServerSocket(puerto);

        // Procesando las solicitudes HTTP en un ciclo infinito.
        while (true) {
                
                Socket socketdeConexion = socketdeEscucha.accept();

                // Construye un objeto para procesar el mensaje de solicitud HTTP.
                SolicitudHttp solicitud = new SolicitudHttp(socketdeConexion);

                // Crea un nuevo hilo para procesar la solicitud.
                Thread hilo = new Thread(solicitud);

                // Inicia el hilo.
                hilo.start();
        }

        }
}

final class SolicitudHttp implements Runnable
{
        final static String CRLF = "\r\n";
        Socket socket;

        // Constructor para recibir el socket.
        public SolicitudHttp(Socket socket) {
                this.socket = socket;
        }

        // Implementa el método run() de la interface Runnable.
        public void run()
        {
                try {
                        proceseSolicitud();
                } catch (Exception e) {
                        System.out.println(e);
                }
        }

        private void proceseSolicitud() throws Exception
        {

        // Referencia al stream de salida del socket.
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        // Referencia y filtros (InputStreamReader y BufferedReader)para el stream de entrada.
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Recoge la línea de solicitud HTTP del mensaje.
        String lineaDeSolicitud = br.readLine();

        // Muestra la línea de solicitud en la pantalla.
        System.out.println();
        System.out.println(lineaDeSolicitud);

        // recoge y muestra las líneas de header.
        String lineaDelHeader = null;
        while ((lineaDelHeader = br.readLine()).length() != 0) {
                System.out.println(lineaDelHeader);
        }

        os.close();
        br.close();
        socket.close();

        }
}
