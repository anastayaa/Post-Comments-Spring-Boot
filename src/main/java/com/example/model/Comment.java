package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Comment extends AuditModel{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Lob
	private String text;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Post post;

	@Builder
	public Comment(@NotNull String text) {
		this.text = text;
	}
}
