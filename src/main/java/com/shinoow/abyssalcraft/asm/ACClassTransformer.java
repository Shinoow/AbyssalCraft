package com.shinoow.abyssalcraft.asm;

import static org.objectweb.asm.Opcodes.FDIV;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.tree.AbstractInsnNode.METHOD_INSN;

import java.util.Iterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ACClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		
		if (arg0.equals("uf")) {
			return patchClassASM(arg0, arg2, true);
			}

			if (arg0.equals("net.minecraft.entity.player")) {
			return patchClassASM(arg0, arg2, false);
			}

			return arg2;
			}

	
	public byte[] patchClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
        
        if(obfuscated == true)
                targetMethodName ="a";
        else
                targetMethodName ="onDeath";
        

      //set up ASM class manipulation stuff. Consult the ASM docs for details
            ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        
       
      //Now we loop over all of the methods declared inside the Explosion class until we get to the targetMethodName "doExplosionB"

        // find method to inject into
        Iterator<MethodNode> methods = classNode.methods.iterator();
        while(methods.hasNext())
        {
            MethodNode m = methods.next();
            System.out.println("********* Method Name: "+m.name + " Desc:" + m.desc);
            int fdiv_index = -1;
            
          //Check if this is doExplosionB and it's method signature is (Z)V which means that it accepts a boolean (Z) and returns a void (V)
            if ((m.name.equals(targetMethodName) && m.desc.equals("(Z)V")))
            {
                System.out.println("********* Inside target method!");
                
                // find interesting instructions in method, there is a single FDIV instruction we use as target
                //System.out.println("m.instructions.size = " + m.instructions.size());
                
                AbstractInsnNode currentNode = null;
                AbstractInsnNode targetNode = null;
                
                Iterator<AbstractInsnNode> iter = m.instructions.iterator();
               
                int index = -1;
                
              //Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
                while (iter.hasNext())
                {
                    index++;
                    currentNode = iter.next();
                    System.out.println("********* index : " + index + " currentNode.getOpcode() = " + currentNode.getOpcode());
                   
                  //Found it! save the index location of instruction FDIV and the node for this instruction
                    if (currentNode.getOpcode() == FDIV)
                    {
                            targetNode = currentNode;
                        fdiv_index = index;
                    }
                }

                    /*
                
                        2013-07-05 18:32:29 [INFO] [STDOUT] ********* index : 334 currentNode.getOpcode() = 25
                                    2013-07-05 18:32:29 [INFO] [STDOUT] ********* index : 335 currentNode.getOpcode() = 180
                                    2013-07-05 18:32:29 [INFO] [STDOUT] ********* index : 336 currentNode.getOpcode() = 110

                         */
                    
                    System.out.println("********* fdiv_index should be 336 -> " + fdiv_index);
                    
                    
                    
                    
                    if (targetNode == null)
                    {
                        System.out.println("Did not find all necessary target nodes! ABANDON CLASS!");
                        return bytes;
                    }
                    
                    if (fdiv_index == -1)
                    {
                        System.out.println("Did not find all necessary target nodes! ABANDON CLASS!");
                        return bytes;
                    }
                   
                    /*
                     //now we want the save nods that load the variable explosionSize and the division instruction:

                     The line in Explosion.java that we want to modify is:
                     
                     var25.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0F / this.explosionSize, 0);
                     
                     The code we are looking for is the following in bytecode:
                     
                     mv.visitInsn(FCONST_1);
                                     mv.visitVarInsn(ALOAD, 0);
                 mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F");
                 mv.visitInsn(FDIV);
                 mv.visitInsn(ICONST_0);
                 mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/src/Block", "dropBlockAsItemWithChance", "(Lnet/minecraft/src/World;IIIIFI)V");
                 */
    
                    AbstractInsnNode remNode1 = m.instructions.get(fdiv_index-2); // mv.visitVarInsn(ALOAD, 0);
                    AbstractInsnNode remNode2 = m.instructions.get(fdiv_index-1); // mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F");
                    AbstractInsnNode remNode3 = m.instructions.get(fdiv_index);   // mv.visitInsn(FDIV);
                   
                    //This part is just to show how if the opcode we are looking for is an invokevirtual
                    
                    AbstractInsnNode invVirt = m.instructions.get(fdiv_index+2);
                    if(invVirt.getOpcode() == INVOKEVIRTUAL)
                    {
                            if(invVirt.getType() == METHOD_INSN){
                                     System.out.println("INVOKEVIRTUAL opcode is  " + invVirt.getOpcode() + " METHOD_INSN type is " + invVirt.getType()  );
                                 
                                     MethodInsnNode testMethod = (MethodInsnNode)invVirt; //only do this cast if the getType match to a MethodInsnNode!! look at the javadoc for the other types!
                                     System.out.println("INVOKEVIRTUAL :" + testMethod.owner + " , " +  testMethod.name + " , " + testMethod.desc );
                            }
                    }
                    
                    
              
                    
                    
                  //just remove these nodes from the instruction set, this will prevent the instruction FCONST_1 to be divided.

                    m.instructions.remove(remNode1);
                    m.instructions.remove(remNode2);
                    m.instructions.remove(remNode3);
                    
                    
                  //in this commented section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

                    /*
                    //To add new instructions, such as calling a static method can be done like so:
                    
                    // make new instruction list
                    InsnList toInject = new InsnList();
                   
                                    toInject.add(new VarInsnNode(ALOAD, 0));
                toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
            
                    // inject new instruction list into method instruction list
                    m.instructions.insert(targetNode, toInject);
                    */
                    
                    System.out.println("Patching Complete!");
                    break;
                }
            }
            
          //ASM specific for cleaning up and returning the final bytes for JVM processing.
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
  }

}
