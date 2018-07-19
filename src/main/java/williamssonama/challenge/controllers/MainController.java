package williamssonama.challenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Simply return the index hpage
 */
@Controller
public class MainController {

//    private JokeService jokeService;
//
//    @Autowired
//    public
// ChuckNorrisController(JokeService jokeService) {
//        this.jokeService = jokeService;
//    }


    public MainController() {
        System.err.println("Yo Yo Yo!!!");
    }

    @GetMapping("/")
    public String getWelcomePage(){
        return "index";
    }

}
