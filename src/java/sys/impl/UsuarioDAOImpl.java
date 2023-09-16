package sys.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import sys.componentes.EncriptarPassword;
import sys.dao.UsuarioDAO;
import sys.modelo.Usuario;
import sys.util.HibernateUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario search(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Usuario WHERE nombreUsuario=:nombreUsuario";
        Query query = session.createQuery(hql);
        query.setParameter("nombreUsuario", usuario.getNombreUsuario());
        return (Usuario) query.uniqueResult();
    }

    @Override
    public Usuario login(Usuario usuario) {
        Usuario usuarioLogin = search(usuario);
        if (usuarioLogin != null) {
            if (!usuarioLogin.getPassword().equals(EncriptarPassword.sha512(usuario.getPassword()))) {
                usuarioLogin = null;
            }
        }
        return usuarioLogin;
    }
}
