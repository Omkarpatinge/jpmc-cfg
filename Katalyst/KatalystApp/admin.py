from django.contrib import admin
from KatalystApp.models import Qualification,Mentor,Mentee,Review,Goals,Appointment,AppointmentChat,Feedback,Reminders
from django.contrib.admin import AdminSite
from django.utils.translation import ugettext_lazy

admin.site.register(Mentor)
admin.site.register(Mentee)
admin.site.register(Appointment)
admin.site.register(Reminders)
admin.site.register(Feedback)
admin.site.register(AppointmentChat)
admin.site.register(Qualification)
admin.site.register(Review)
admin.site.register(Goals)

class MyAdminSite(AdminSite):
    # Text to put at the end of each page's <title>.
    site_title = ugettext_lazy('Katalyst Admin')

    # Text to put in each page's <h1>.
    site_header = ugettext_lazy('Katalyst administration')

    # Text to put at the top of the admin index page.
    index_title = ugettext_lazy('Katalyst administration')

admin_site = MyAdminSite()
