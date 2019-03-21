package com.stufusion.nlp.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * @author Sunil
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class GenericRequest implements Serializable {


    private static final long serialVersionUID = 1L;


}
