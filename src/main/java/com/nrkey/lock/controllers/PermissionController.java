package com.nrkey.lock.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrkey.lock.models.Permission;
import com.nrkey.lock.services.PermissionService;

@RestController
public class PermissionController {
	private PermissionService ps;
	
	public PermissionController(PermissionService ps) {
		this.ps = ps;
	}
	
	@RequestMapping(value="/api/makePermission", method=RequestMethod.POST)
	public String makePermission(HttpServletResponse res, HttpSession session, Model model, @Valid @ModelAttribute("newPermission") Permission perm) {
		ps.makePermission(perm);
		try {
			return new ObjectMapper().writeValueAsString("{msg:'Data successfully updated'}");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "uh oh";
		}
	}
	
	@RequestMapping("/api/permissions/{id}")
	public String getPermission(@PathVariable("id") Long id) {
		Permission perm = this.ps.findById(id);
		if(perm != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(perm);
				System.out.println(json);
				return json;
			} catch (IOException e) {
				e.printStackTrace();
				return "{message: 'Error'}";
			}
		} else {
			return "redirect:/lock";
		}
	}
	
	@RequestMapping("/api/permissions/delete/{id}")
	public String deletePermission(@PathVariable("id") Long id) {
		ps.deletePermission(id);
		return "{msg:'success'}";
	}
}