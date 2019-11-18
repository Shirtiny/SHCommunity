package cn.shirtiny.community.SHcommunity.Shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

//关闭session
public class NoSessionWebSubjectFactory extends DefaultWebSubjectFactory {
    @Override
    public Subject createSubject(SubjectContext context) {
        //不使用session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
