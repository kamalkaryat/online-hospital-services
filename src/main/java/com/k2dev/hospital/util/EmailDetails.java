package com.k2dev.hospital.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailDetails {
	 
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}