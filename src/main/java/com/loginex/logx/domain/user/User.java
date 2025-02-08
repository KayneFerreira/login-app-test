package com.loginex.logx.domain.user;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@NotBlank(message = "Nome obrigatório")
	private String name;
	
	@NotBlank(message = "Email obrigatório")
	@Column(unique = true)
	@Email(message = "Formato de email inválido")
	private String email;
	
	@NotBlank(message = "Senha obrigatória")
	@Length(min = 6)
	private String password;
	
}
