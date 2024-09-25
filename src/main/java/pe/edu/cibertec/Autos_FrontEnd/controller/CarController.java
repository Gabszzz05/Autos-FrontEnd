package pe.edu.cibertec.Autos_FrontEnd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.Autos_FrontEnd.viewModel.CarModel;

@Controller
@RequestMapping("/wheels")
public class CarController {

    @GetMapping("/inicio")
    public String inicioPage(Model model) {
        CarModel carModel = new CarModel("00", "", "");
        model.addAttribute("carModel", carModel);
        return "inicioPage";
    }

    @PostMapping("/find")
    public String find(@RequestParam("placa") String placa, Model model) {
        if(placa == null || placa.isEmpty()){
            //Error message
            CarModel carModel = new CarModel("01", "ERROR: Completar todos los campos", "");
            model.addAttribute("carModel",carModel);
            return "inicioPage";
        }

        //Use API



        return "principalPage";
    }

}
