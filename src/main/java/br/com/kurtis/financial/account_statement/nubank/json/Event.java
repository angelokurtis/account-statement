package br.com.kurtis.financial.account_statement.nubank.json;

import br.com.kurtis.financial.infra.ConfigProperties;
import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "description",
        "category",
        "amount",
        "time",
        "title",
        "details",
        "id",
        "_links",
        "href"
})
public class Event {

    @JsonIgnore
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    @JsonIgnore
    private static final DateTimeFormatter DATE_STRING_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private String category;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("time")
    private String time;
    @JsonProperty("title")
    private String title;
    @JsonProperty("details")
    private Details details;
    @JsonProperty("id")
    private String id;
    @JsonProperty("_links")
    private Links links;
    @JsonProperty("href")
    private String href;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("details")
    public Details getDetails() {
        return details;
    }

    @JsonProperty("details")
    public void setDetails(Details details) {
        this.details = details;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonIgnore
    public Integer getChargeValue() {
        return getDetails().getCharges().getAmount();
    }

    @JsonIgnore
    public LocalDateTime getDateTime() {
        final String timeZoneId = ConfigProperties.getProperty("app.time_zone");
        final TimeZone zone = TimeZone.getTimeZone(timeZoneId);
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(DATETIME_FORMAT)
                .withZone(zone.toZoneId());

        return LocalDateTime.parse(getTime(), formatter);
    }

    @JsonIgnore
    public LocalDate getDueDate() {
        final LocalDateTime date = getDateTime();
        final Integer dueDate = Integer.valueOf(ConfigProperties.getProperty("nubank.due_date"));
        return date.getDayOfMonth() >= (dueDate - 7) ? date.plusMonths(1).withDayOfMonth(dueDate).toLocalDate() : date.withDayOfMonth(dueDate).toLocalDate();
    }

    @JsonIgnore
    public String getFullDescription() {
        return '[' + getDateTime().format(DATE_STRING_FORMATTER) + "] " + getDescription();
    }

    @JsonIgnore
    public int getNumberOfCharges() {
        return hasCharges() ? getDetails().getCharges().getCount() : 1;
    }

    @JsonIgnore
    public BigDecimal getValue() {
        final String amount = hasCharges() ? getChargeValue().toString() : getAmount().toString();
        final String valueString = new StringBuilder(amount).insert(amount.length() - 2, ".").toString();
        System.out.println(getFullDescription() + " " + valueString);
        return new BigDecimal(valueString).multiply(BigDecimal.ONE.negate());
    }

    @JsonIgnore
    public boolean hasCharges() {
        return getDetails().getCharges() != null;
    }
}
