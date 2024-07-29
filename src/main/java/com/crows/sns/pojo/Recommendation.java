package com.crows.sns.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {
    @JsonProperty("id")
	private int recommendation_id;
	private String companyName;
    int user_id;
    private String author;
    private String title;
    private String content;
    private String summary;
    @JsonProperty("city")
    private String location;
    @JsonProperty("approved")
    boolean status;
    @JsonProperty("salaryRange")
    private SalaryRange salaryRange = new SalaryRange();
    private int max_salary;
    private int min_salary;

    public void makeSalaryRange() {
        this.salaryRange.setMax_salary(max_salary);
        this.salaryRange.setMin_salary(min_salary);
    }
}
