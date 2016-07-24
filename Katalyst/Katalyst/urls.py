from django.conf.urls import patterns, include, url
from django.contrib import admin
from KatalystApp.admin import admin_site
urlpatterns = patterns('',

    url(r'^admin/', include(admin.site.urls)),
    url(r'^updateGcmMentor/$', 'KatalystApp.views.updateGcmMentor', name='Mentor update gcm'),
    url(r'^updateGcmMentee/$', 'KatalystApp.views.updateGcmMentee', name='Mentee update gcm'),
    url(r'^getMyChatsMentor/$', 'KatalystApp.views.getMyChatsMentor', name='getMyChats'),
    url(r'^getMyChatsMentee/$', 'KatalystApp.views.getMyChatsMentee', name='getMyChats'),
    url(r'^getChatHistoryMentor/$', 'KatalystApp.views.getChatHistoryMentor', name='getMyChatsHistory'),
    url(r'^getChatHistoryMentee/$', 'KatalystApp.views.getChatHistoryMentee', name='getMyChatsHistory'),
    url(r'^addQueryMessageMentor/$', 'KatalystApp.views.addQueryMessageMentor', name='addMsg'),
    url(r'^addQueryMessageMentee/$', 'KatalystApp.views.addQueryMessageMentee', name='addMsg'),
    url(r'^dashboard/$', 'KatalystApp.views.dashboard', name='dashboard'),
    url(r'^mentor/$', 'KatalystApp.views.mentor', name='mentor'),
    url(r'^mentee/$', 'KatalystApp.views.mentee', name='mentee'),
    url(r'^getGoals/$', 'KatalystApp.views.getGoals', name='getGoals'),
    url(r'^getSms/$', 'KatalystApp.views.getSMS', name='getSms'),
    url(r'^allMentors/$', 'KatalystApp.views.allMentors', name='allMentors'),
    url(r'^allMentees/$', 'KatalystApp.views.allMentees', name='allMentees'),
    url(r'^meeting/$', 'KatalystApp.views.meeting', name='meeting'),
    url(r'^analytics/$', 'KatalystApp.views.analytics', name='analytics'),
    url(r'^analytic/$', 'KatalystApp.views.analytic', name='analytic'),
    url(r'^getAppointmentDetails/$', 'KatalystApp.views.createAppt', name='getAppointmentDetails'),
    url(r'^getFeedback/$', 'KatalystApp.views.getFeedback', name='getFeedback'),
    url(r'^feedbacks/$', 'KatalystApp.views.feedbacks', name='feedbacks'),
    url(r'^meetings/$', 'KatalystApp.views.meetings', name='meetings'),
    url(r'^allPairings/$', 'KatalystApp.views.allPairings', name='allPairings'),
    url(r'^addQueryMessage/$', 'KatalystApp.views.addQueryMessage', name='addQueryMessage'),
    url(r'^anyMessage/$', 'KatalystApp.views.anyMessage', name='anyMessage'),

)
