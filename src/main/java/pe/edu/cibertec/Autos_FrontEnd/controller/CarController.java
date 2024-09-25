package pe.edu.cibertec.Autos_FrontEnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.Autos_FrontEnd.dto.CarRequestDTO;
import pe.edu.cibertec.Autos_FrontEnd.dto.CarResponseDTO;
import pe.edu.cibertec.Autos_FrontEnd.viewModel.CarModel;

@Controller
@RequestMapping("/wheels")
public class CarController {
    @Autowired
    RestTemplate restTemplate;

    private final String backendURL = "http://localhost:8083/searching/searched";

    @GetMapping("/inicio")
    public String inicioPage(Model model) {
        CarModel carModel = new CarModel("00", "", "", "", "", 0, 0.0, "");
        model.addAttribute("carModel", carModel);
        return "inicioPage";
    }

    @PostMapping("/find")
    public String find(@RequestParam("placa") String placa, Model model) {
        if(placa == null || placa.isEmpty() || !placa.matches("^[A-Z0-9]{3}-[0-9]{3}$")){
            //Error message
            CarModel carModel = new CarModel("01", "ERROR: Debe ingresar una placa correcta", "", "", "", 0, 0.0, "");
            model.addAttribute("carModel",carModel);
            return "inicioPage";
        }

        //Use API
        try{
            CarRequestDTO carRequestDTO = new CarRequestDTO(placa);
            CarResponseDTO carResponseDTO = restTemplate.postForObject(backendURL, carRequestDTO, CarResponseDTO.class);

            if(carResponseDTO.code().equals("00")){
                CarModel carModel = new CarModel("00", "", "", carResponseDTO.marca(), carResponseDTO.modelo(), carResponseDTO.asientos(), carResponseDTO.precio(), carResponseDTO.color());
                model.addAttribute("carModel",carModel);
                return "principalPage";
            }else{
                CarModel carModel = new CarModel("01", "ERROR: Busqueda fallida", "", "", "", 0, 0.0, "");
                model.addAttribute("carModel",carModel);
                return "inicioPage";
            }
        }catch (Exception e){
            CarModel carModel = new CarModel("99", "ERROR: No se pudo conectar con el servidor", "", "", "", 0, 0.0, "");
            model.addAttribute("carModel",carModel);
            System.out.println(e.getMessage());
            return "inicioPage";
        }
    }

}
