package esm.exception;

import org.springframework.validation.BindingResult;
public class InvalidInputException extends RuntimeException{

    private BindingResult bindingResult;

    public InvalidInputException(BindingResult br){
        this.bindingResult = br;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
