
package cszz.compiler;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
/**
 *
 * @author Kason Yang 
 */
public interface SourceLoader {
    
    @Nullable
    CszzSource loadSource(@Nonnull String className);
    
}
