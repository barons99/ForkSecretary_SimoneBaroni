package it.dedagroup.project_cea.controller;

import java.time.LocalDate;
import java.util.List;

import it.dedagroup.project_cea.dto.response.CondominiumDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.project_cea.dto.response.BillDTOResponse;
import it.dedagroup.project_cea.dto.response.InterventionDTOResponse;
import it.dedagroup.project_cea.dto.response.ScanDTOResponse;
import it.dedagroup.project_cea.facade.SecretaryFacade;
import it.dedagroup.project_cea.model.TypeOfIntervention;
import static it.dedagroup.project_cea.util.UtilPath.*;

@RestController
@RequestMapping("/secretary")
public class SecretaryController {
	
	@Autowired
	SecretaryFacade secFac;
	
	@GetMapping(GET_ALL_BILLS_OF_CONDOMINIUM_PATH)
	public ResponseEntity<List<BillDTOResponse>> getAllBillsOfCondominium(@PathVariable long idCondominium){
		return ResponseEntity.status(HttpStatus.OK).body(secFac.getAllBillsOfCondominium(idCondominium));
	}
	
	@GetMapping(GET_INTERVENTION_LIST_PER_TYPE_PATH+"{interv}")
	public ResponseEntity<List<InterventionDTOResponse>> getInterventionListPerType(@PathVariable TypeOfIntervention interv){
		return ResponseEntity.status(HttpStatus.OK).body(secFac.getInterventionListPerType(interv));
	}
	
	@GetMapping(GET_SCANS_PATH)
	public ResponseEntity<List<ScanDTOResponse>> getScans(){
		return ResponseEntity.status(HttpStatus.OK).body(secFac.getScans());
	}

	@GetMapping(REMOTE_SCAN_PATH+"{idApartment}/{liters}/{scanDate}")
	public ResponseEntity remoteScan(@PathVariable Long idApartment, double liters, LocalDate scanDate) {
		return ResponseEntity.status(HttpStatus.CREATED).body(secFac.RemoteScan(idApartment, liters, scanDate));
	}

	@GetMapping(WORKLOAD_PATH+"{maxWorkload}")
	public String setWorkload(@PathVariable int maxWorkload) {
		if (maxWorkload >= 0) {
			secFac.setWorkload(maxWorkload);
			return "max: " + maxWorkload;
		} else {
			return "no negative!";
		}
	}
	@PostMapping(ACCEPT_PENDING_INTERVENTION_PATH+"{idApartment}/{idIntervention}")
	public ResponseEntity<InterventionDTOResponse> acceptPendingIntervention(@PathVariable long idApartment, @PathVariable long idIntervention){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(secFac.acceptPendingIntervention(idApartment, idIntervention));
	}

	@GetMapping(LIST_OF_CONDOMINIUM_OF_TECHNICIAN_INTERVENTIONS_PATH+"{idTechnician}")
		public ResponseEntity<List<CondominiumDtoResponse>> listOfCondominiumOfInterventionsOfTechnician(@PathVariable long idTechnician){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(secFac.listaCondominiDiInterventiTecnico(idTechnician));
		}
}
