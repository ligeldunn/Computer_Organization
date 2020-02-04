import static org.junit.Assert.assertTrue;

/**
 * Class with methods to implement the basic arithmetic operations by
 * operating on bit fields.
 *
 */

public class BinaryCalculator
{
	// Logical Right Shift by 1 method
	public static BitField logicalRightS(BitField a)
    {
    		BitField temp = new BitField(a.size());
    		if(a.get(a.size()-1)){
    			temp.set(a.size()-1, true);}
    		else{
    			temp.set(a.size()-1, false);	
    		}
    		
    		for(int i=0; i<a.size()-1; i++){
    			temp.set(i, a.get(i+1));
    		}
    		return temp;
    }
	
	 // Method to "pad" back of BitField
    public static BitField rightPad(BitField a){
    	BitField temp = new BitField(a.size()*2);
    	
    	for(int i=0; i<a.size()*2; i++){
    		if(i<a.size()){
    			temp.set(i, a.get(i));
    		}
    		else{
    			temp.set(i, false);
    		}
    	}
    	return temp;
    }
 	
	// Logical Left Shift by 1 method
    public static BitField logicalLeftS(BitField a)
    {
    		BitField temp = new BitField(a.size());
    		temp.set(0, false);
    		for(int i=0; i<a.size()-1; i++){
    			temp.set(i+1, a.get(i));
    		}
    		return temp;
    }
    
    // Method to "pad" front of BitField
    public static BitField leftPad(BitField a){
    	BitField temp = new BitField(a.size()*2);
    	
    	for(int i=0; i<a.size()*2; i++){
    		if(i>=a.size()){
    			temp.set(i, a.get(i-a.size()));
    		}
    		else{
    			temp.set(i, false);
    		}
    	}
    	return temp;
    }
    
   
	// 2's complement conversion method
    public static BitField complement(BitField a){
		
		for(int i = 0; i < a.size(); i++) {
			if(a.get(i) == true) {
				a.setFalse(i);
			}else {
			a.setTrue(i);
		}
		}
		if(a.get(0) == false) {
			a.set(0, true );
			return a;
		}else {
	
		for(int i = 0; i < a.size(); i++) {
	
		if(a.get(i) == true) {

			a.set(i, false);
			
		}else if(a.get(i) == false) {
			a.set(i, true);
			break;
		}
		
		}
		
			
		}
		return a;
	}
    
    // Add method
	public static BitField add(BitField a, BitField b)
    {
	if(null == a || null == b || a.size() != b.size()){
	    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
	}
		
		int sizee = a.size();
		
		boolean[] bitarray = new boolean[sizee]; 
		boolean carry = false;
		String temp = "";
		// Loop through
		for(int i = 0; i < sizee; i++) {
			
		// If statements needed for addition
		if((a.get(i) == true && b.get(i) == true)) {
			
			if(carry == true) {
				bitarray[i] = true;
				
				carry = true;
			}else {
			bitarray[i] = false;
			
			carry = true;
			}
		}
		
		if((a.get(i) == false && b.get(i) == false)) {
			if(carry == true) {
				bitarray[i] = true;
				
				carry = false;
			}else {
			bitarray[i] = false;
			
			carry = false;
			}
		}
		
		if((a.get(i) == true && b.get(i) == false) || (b.get(i) == true && a.get(i) == false) ) {
			
			if(carry == true) {
				bitarray[i] = false;
				
				carry = true;
			}else {
			bitarray[i] = true;
			
			carry = false;
			}
		}
		
		
		}
	// Convert bool Array to String	
	for(int i = sizee-1; i >= 0; i--) {
		if(bitarray[i] == true ) {
			temp += "1";
		}else {
			temp += "0";
		}
	
	}
	// Return String BitField Constructor
	return new BitField(temp);
    
    }
	// Subtract method
    public static BitField subtract(BitField a, BitField b)
    {
	if(null == a || null == b || a.size() != b.size()){
	    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
	}
	
	// do 2's complement to b and then add a and b 
	BitField temp = complement(b);
	
	BitField temp1 = add(a,temp);
	String temp2 = temp1.toString();
	
	// Return String BitField Constructor
	return new BitField(temp2);
    }
    
    // Multiply method
    public static BitField multiply(BitField a, BitField b)
    {
	if(null == a || null == b || a.size() != b.size()){
	    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
	}
	// Create copies
	BitField temp = a.copy();
	BitField temp1 = b.copy();
	
	// Temp variable needed
	BitField bitTemp = new BitField(a.size());

	// Loop Through 
	for(int i =0; i <a.size(); i++){
		
		if(temp.get(0)){
			bitTemp = add(bitTemp, temp1);
		}	
		// Do a Right and Left shift by 1
		temp = logicalRightS(temp);
		temp1 = logicalLeftS(temp1);
		
	}
	
	// Return the BitField object
	return bitTemp;
	
    }

    // Divide Method
    public static BitField[] divide(BitField a, BitField b)
    {
	if(null == a || null == b || a.size() != b.size()){
	    throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
	}
	
	// No divide by zero
	if(b.toLongSigned() == 0) {
		return null;
	}
	
	// Variables needed
	int sizee = a.size();
	boolean temp = false;
	BitField[] out = new BitField [ 2 ];
	BitField BitTemp = null;

	
	
	if(a.get(a.size()-1) == true) {
		
		// Call to pad method (signed #)
		out[1] = rightPad(complement(a.copy()));
		
		if(temp == true) {
			temp = false;
		}else {
			temp = true;
		}
	}else {
		// Call to pad method (unsigned #)
		out[1] = rightPad(a.copy());
		
	}
		
	if(b.get(b.size()-1) == true) {
		// Call to pad method (signed #)
		BitTemp = leftPad(complement(b.copy()));
		
		if(temp == true) {
			temp = false;
		}else {
			temp = true;
		}
	}else {
		// Call to pad method (unsigned #)
		BitTemp = leftPad(b.copy());
		
	}
	// Deal with remainder
	BitField r = new BitField(a.size());
	
	BitField temp1 = new BitField(sizee);
	for(int i=0; i<sizee; i++){
		temp1.set(i, a.get(i));
	}
	
	r = temp1;
	
	out[0] = new BitField(a.size());
	out[1] = r;
	
	// Return out
	return out;
    }
    
    
}
