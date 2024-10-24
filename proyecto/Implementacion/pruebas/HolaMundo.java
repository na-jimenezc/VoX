    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class HolaMundo {
    
        @GetMapping("/")
        public String getMessage()  {
            return "Hola Mundo !!";
        }

    }