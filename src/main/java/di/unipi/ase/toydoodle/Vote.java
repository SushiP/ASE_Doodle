package di.unipi.ase.toydoodle;

import javax.validation.constraints.NotNull;

public class Vote {
    @NotNull
    private String name;
    @NotNull
    private String option;

    public Vote(){ }

    public Vote( String name, String option){
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return option;
    }
}
