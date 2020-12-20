package university_departments.entities.dto;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 18.12.2020
 */
public class StatisticDto {
    private int assistantCount;
    private int professorCount;
    private int associateProfessorCount;

    public int getAssistantCount() {
        return assistantCount;
    }

    public void setAssistantCount(int assistantCount) {
        this.assistantCount = assistantCount;
    }

    public int getProfessorCount() {
        return professorCount;
    }

    public void setProfessorCount(int professorCount) {
        this.professorCount = professorCount;
    }

    public int getAssociateProfessorCount() {
        return associateProfessorCount;
    }

    public void setAssociateProfessorCount(int associateProfessorCount) {
        this.associateProfessorCount = associateProfessorCount;
    }
}
