package williamssonama.challenge.controllers;

public class MainController {
}

@Controller
public class ChuckNorrisController {

    private JokeService jokeService;

    @Autowired
    public ChuckNorrisController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @RequestMapping("/")
    public String getTheYoke(Model model){

        model.addAttribute("joke", jokeService.getRandomJoke());

        return "chucknorris";
    }

}
