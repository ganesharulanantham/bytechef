package com.bytechef.platform.configuration.web.rest.model;

import java.net.URI;
import java.util.Objects;
import com.bytechef.platform.configuration.web.rest.model.ControlTypeModel;
import com.bytechef.platform.configuration.web.rest.model.PropertyModel;
import com.bytechef.platform.configuration.web.rest.model.PropertyTypeModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * A base property for all value based properties.
 */

@Schema(name = "ValueProperty", description = "A base property for all value based properties.")
@JsonIgnoreProperties(
  value = "type", // ignore manually set type, it will be automatically generated by Jackson during serialization
  allowSetters = true // allows the type to be set during deserialization
)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ArrayPropertyModel.class, name = "ARRAY"),
  @JsonSubTypes.Type(value = BooleanPropertyModel.class, name = "BOOLEAN"),
  @JsonSubTypes.Type(value = DatePropertyModel.class, name = "DATE"),
  @JsonSubTypes.Type(value = DateTimePropertyModel.class, name = "DATE_TIME"),
  @JsonSubTypes.Type(value = DynamicPropertiesPropertyModel.class, name = "DYNAMIC_PROPERTIES"),
  @JsonSubTypes.Type(value = FileEntryPropertyModel.class, name = "FILE_ENTRY"),
  @JsonSubTypes.Type(value = IntegerPropertyModel.class, name = "INTEGER"),
  @JsonSubTypes.Type(value = NullPropertyModel.class, name = "NULL"),
  @JsonSubTypes.Type(value = NumberPropertyModel.class, name = "NUMBER"),
  @JsonSubTypes.Type(value = ObjectPropertyModel.class, name = "OBJECT"),
  @JsonSubTypes.Type(value = StringPropertyModel.class, name = "STRING"),
  @JsonSubTypes.Type(value = TaskPropertyModel.class, name = "TASK"),
  @JsonSubTypes.Type(value = TimePropertyModel.class, name = "TIME")
})

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-14T19:06:43.949922+02:00[Europe/Zagreb]", comments = "Generator version: 7.8.0")
public class ValuePropertyModel extends PropertyModel {

  private ControlTypeModel controlType;

  private String label;

  private String placeholder;

  public ValuePropertyModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ValuePropertyModel(ControlTypeModel controlType, PropertyTypeModel type) {
    super(type);
    this.controlType = controlType;
  }

  public ValuePropertyModel controlType(ControlTypeModel controlType) {
    this.controlType = controlType;
    return this;
  }

  /**
   * Get controlType
   * @return controlType
   */
  @NotNull @Valid 
  @Schema(name = "controlType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("controlType")
  public ControlTypeModel getControlType() {
    return controlType;
  }

  public void setControlType(ControlTypeModel controlType) {
    this.controlType = controlType;
  }

  public ValuePropertyModel label(String label) {
    this.label = label;
    return this;
  }

  /**
   * The property label.
   * @return label
   */
  
  @Schema(name = "label", description = "The property label.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public ValuePropertyModel placeholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  /**
   * The property placeholder.
   * @return placeholder
   */
  
  @Schema(name = "placeholder", description = "The property placeholder.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("placeholder")
  public String getPlaceholder() {
    return placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }


  public ValuePropertyModel advancedOption(Boolean advancedOption) {
    super.advancedOption(advancedOption);
    return this;
  }

  public ValuePropertyModel description(String description) {
    super.description(description);
    return this;
  }

  public ValuePropertyModel displayCondition(String displayCondition) {
    super.displayCondition(displayCondition);
    return this;
  }

  public ValuePropertyModel expressionEnabled(Boolean expressionEnabled) {
    super.expressionEnabled(expressionEnabled);
    return this;
  }

  public ValuePropertyModel hidden(Boolean hidden) {
    super.hidden(hidden);
    return this;
  }

  public ValuePropertyModel name(String name) {
    super.name(name);
    return this;
  }

  public ValuePropertyModel required(Boolean required) {
    super.required(required);
    return this;
  }

  public ValuePropertyModel type(PropertyTypeModel type) {
    super.type(type);
    return this;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValuePropertyModel valueProperty = (ValuePropertyModel) o;
    return Objects.equals(this.controlType, valueProperty.controlType) &&
        Objects.equals(this.label, valueProperty.label) &&
        Objects.equals(this.placeholder, valueProperty.placeholder) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(controlType, label, placeholder, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValuePropertyModel {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    controlType: ").append(toIndentedString(controlType)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

