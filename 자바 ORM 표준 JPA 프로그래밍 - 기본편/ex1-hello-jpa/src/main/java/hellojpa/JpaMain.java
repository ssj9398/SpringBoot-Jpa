package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
////          //비영속
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L,"B");
//
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("====================");
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            //영속
            Member member = new Member(200L, "member200");
            em.persist(member);
            em.flush();
            System.out.println("=================");
//            System.out.println("=== BEFORE ===");
//            em.persist(member);
//            System.out.println("=== AFTER ===");
//
//            Member findMember = em.find(Member.class, 101L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
