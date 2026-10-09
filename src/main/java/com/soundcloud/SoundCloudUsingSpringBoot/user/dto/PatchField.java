package com.soundcloud.SoundCloudUsingSpringBoot.user.dto;

public final class PatchField<T> {
    
    /**
     * provided | value	   | Meaning
     * ------------------------------------------------------
     * false	| null	   | Property omitted
     * true	    | null	   | Property explicitly cleared
     * true	    | Non-null | Property supplied with a value
     */



    private final boolean provided;
    private final T value;
    
    
    private PatchField(boolean provided, T value) {
        this.provided = provided;
        this.value = value;
    }

    // Creates a field that wasn't supplied
    // The <T> before the return type (PatchField<T>) declares the generic type parameter for this static method.
    public static <T> PatchField<T> undefined(){
        return new PatchField<T>(false, null);
    }

    // The property was supplied, regardless if its value is null or not
    public static <T> PatchField<T> of(T value){
        return new PatchField<T>(true, value);
    }

    
    public boolean isProvided() {
        return provided;
    }

    public T getValue() {
        return value;
    }


    
    
}
