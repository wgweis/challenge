package williamssonama.challenge.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import williamssonama.challenge.model.ResponseDataTransferObject;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class ReducerRestResponder {

    private Gson gson;

    @Autowired
    public ReducerRestResponder(Gson gson) {
System.err.println("On my Way, Baby!!!");
        this.gson = gson;
    }

    @RequestMapping(value = "reducer", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getEbrsCcbfExportReportValidation(HttpServletRequest request)
    {
        ResponseDataTransferObject rdto = new ResponseDataTransferObject();
        Map<String, String[]> parameters = request.getParameterMap();

        return new ResponseEntity<>(gson.toJson(rdto), HttpStatus.OK);
    }

}
